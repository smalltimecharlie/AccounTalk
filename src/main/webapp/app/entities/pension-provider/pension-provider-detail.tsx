import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pension-provider.reducer';
import { IPensionProvider } from 'app/shared/model/pension-provider.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPensionProviderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PensionProviderDetail extends React.Component<IPensionProviderDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { pensionProviderEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.pensionProvider.detail.title">PensionProvider</Translate> [
            <b>{pensionProviderEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nameOfProvider">
                <Translate contentKey="accounTalkApp.pensionProvider.nameOfProvider">Name Of Provider</Translate>
              </span>
            </dt>
            <dd>{pensionProviderEntity.nameOfProvider}</dd>
            <dt>
              <span id="membershipNumber">
                <Translate contentKey="accounTalkApp.pensionProvider.membershipNumber">Membership Number</Translate>
              </span>
            </dt>
            <dd>{pensionProviderEntity.membershipNumber}</dd>
            <dt>
              <span id="payeReference">
                <Translate contentKey="accounTalkApp.pensionProvider.payeReference">Paye Reference</Translate>
              </span>
            </dt>
            <dd>{pensionProviderEntity.payeReference}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.pensionProvider.client">Client</Translate>
            </dt>
            <dd>{pensionProviderEntity.client ? pensionProviderEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/pension-provider" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/pension-provider/${pensionProviderEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ pensionProvider }: IRootState) => ({
  pensionProviderEntity: pensionProvider.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PensionProviderDetail);
