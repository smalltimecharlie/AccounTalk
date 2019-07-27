import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shares.reducer';
import { IShares } from 'app/shared/model/shares.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISharesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISharesUpdateState {
  isNew: boolean;
  clientId: string;
}

export class SharesUpdate extends React.Component<ISharesUpdateProps, ISharesUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: '0',
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

    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { sharesEntity } = this.props;
      const entity = {
        ...sharesEntity,
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
    this.props.history.push('/entity/shares');
  };

  render() {
    const { sharesEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.shares.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.shares.home.createOrEditLabel">Create or edit a Shares</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : sharesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="shares-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="shares-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="companyNameLabel" for="shares-companyName">
                    <Translate contentKey="accounTalkApp.shares.companyName">Company Name</Translate>
                  </Label>
                  <AvField id="shares-companyName" type="text" name="companyName" />
                </AvGroup>
                <AvGroup>
                  <Label id="numberOfSharesLabel" for="shares-numberOfShares">
                    <Translate contentKey="accounTalkApp.shares.numberOfShares">Number Of Shares</Translate>
                  </Label>
                  <AvField id="shares-numberOfShares" type="string" className="form-control" name="numberOfShares" />
                </AvGroup>
                <AvGroup>
                  <Label for="shares-client">
                    <Translate contentKey="accounTalkApp.shares.client">Client</Translate>
                  </Label>
                  <AvInput id="shares-client" type="select" className="form-control" name="client.id">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/shares" replace color="info">
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
  clients: storeState.client.entities,
  sharesEntity: storeState.shares.entity,
  loading: storeState.shares.loading,
  updating: storeState.shares.updating,
  updateSuccess: storeState.shares.updateSuccess
});

const mapDispatchToProps = {
  getClients,
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
)(SharesUpdate);
