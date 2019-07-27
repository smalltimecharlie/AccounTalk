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
import { getEntity, updateEntity, createEntity, reset } from './pension-provider.reducer';
import { IPensionProvider } from 'app/shared/model/pension-provider.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPensionProviderUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPensionProviderUpdateState {
  isNew: boolean;
  clientId: string;
}

export class PensionProviderUpdate extends React.Component<IPensionProviderUpdateProps, IPensionProviderUpdateState> {
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
      const { pensionProviderEntity } = this.props;
      const entity = {
        ...pensionProviderEntity,
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
    this.props.history.push('/entity/pension-provider');
  };

  render() {
    const { pensionProviderEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.pensionProvider.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.pensionProvider.home.createOrEditLabel">Create or edit a PensionProvider</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : pensionProviderEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="pension-provider-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="pension-provider-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameOfProviderLabel" for="pension-provider-nameOfProvider">
                    <Translate contentKey="accounTalkApp.pensionProvider.nameOfProvider">Name Of Provider</Translate>
                  </Label>
                  <AvField id="pension-provider-nameOfProvider" type="text" name="nameOfProvider" />
                </AvGroup>
                <AvGroup>
                  <Label id="membershipNumberLabel" for="pension-provider-membershipNumber">
                    <Translate contentKey="accounTalkApp.pensionProvider.membershipNumber">Membership Number</Translate>
                  </Label>
                  <AvField id="pension-provider-membershipNumber" type="text" name="membershipNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="payeReferenceLabel" for="pension-provider-payeReference">
                    <Translate contentKey="accounTalkApp.pensionProvider.payeReference">Paye Reference</Translate>
                  </Label>
                  <AvField id="pension-provider-payeReference" type="text" name="payeReference" />
                </AvGroup>
                <AvGroup>
                  <Label for="pension-provider-client">
                    <Translate contentKey="accounTalkApp.pensionProvider.client">Client</Translate>
                  </Label>
                  <AvInput id="pension-provider-client" type="select" className="form-control" name="client.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/pension-provider" replace color="info">
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
  pensionProviderEntity: storeState.pensionProvider.entity,
  loading: storeState.pensionProvider.loading,
  updating: storeState.pensionProvider.updating,
  updateSuccess: storeState.pensionProvider.updateSuccess
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
)(PensionProviderUpdate);
