import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { getEntities as getTaxReturns } from 'app/entities/tax-return/tax-return.reducer';
import { getEntity, updateEntity, createEntity, reset } from './gift-aid-donations.reducer';
import { IGiftAidDonations } from 'app/shared/model/gift-aid-donations.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGiftAidDonationsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IGiftAidDonationsUpdateState {
  isNew: boolean;
  taxReturnId: string;
}

export class GiftAidDonationsUpdate extends React.Component<IGiftAidDonationsUpdateProps, IGiftAidDonationsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getTaxReturns();
  }

  saveEntity = (event, errors, values) => {
    values.donationDate = convertDateTimeToServer(values.donationDate);

    if (errors.length === 0) {
      const { giftAidDonationsEntity } = this.props;
      const entity = {
        ...giftAidDonationsEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/gift-aid-donations');
  };

  render() {
    const { giftAidDonationsEntity, taxReturns, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.giftAidDonations.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.giftAidDonations.home.createOrEditLabel">Create or edit a GiftAidDonations</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : giftAidDonationsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="gift-aid-donations-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="gift-aid-donations-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="charityNameLabel" for="gift-aid-donations-charityName">
                    <Translate contentKey="accounTalkApp.giftAidDonations.charityName">Charity Name</Translate>
                  </Label>
                  <AvField id="gift-aid-donations-charityName" type="text" name="charityName" />
                </AvGroup>
                <AvGroup>
                  <Label id="donationDateLabel" for="gift-aid-donations-donationDate">
                    <Translate contentKey="accounTalkApp.giftAidDonations.donationDate">Donation Date</Translate>
                  </Label>
                  <AvInput
                    id="gift-aid-donations-donationDate"
                    type="datetime-local"
                    className="form-control"
                    name="donationDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.giftAidDonationsEntity.donationDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="donationAmountLabel" for="gift-aid-donations-donationAmount">
                    <Translate contentKey="accounTalkApp.giftAidDonations.donationAmount">Donation Amount</Translate>
                  </Label>
                  <AvField id="gift-aid-donations-donationAmount" type="string" className="form-control" name="donationAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="giftAidClaimedLabel" check>
                    <AvInput id="gift-aid-donations-giftAidClaimed" type="checkbox" className="form-control" name="giftAidClaimed" />
                    <Translate contentKey="accounTalkApp.giftAidDonations.giftAidClaimed">Gift Aid Claimed</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="gift-aid-donations-taxReturn">
                    <Translate contentKey="accounTalkApp.giftAidDonations.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="gift-aid-donations-taxReturn" type="select" className="form-control" name="taxReturn.id">
                    <option value="" key="0" />
                    {taxReturns
                      ? taxReturns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/gift-aid-donations" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  taxReturns: storeState.taxReturn.entities,
  giftAidDonationsEntity: storeState.giftAidDonations.entity,
  loading: storeState.giftAidDonations.loading,
  updating: storeState.giftAidDonations.updating,
  updateSuccess: storeState.giftAidDonations.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GiftAidDonationsUpdate);
